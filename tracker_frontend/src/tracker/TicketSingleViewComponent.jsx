/** @jsxImportSource @emotion/react */

import { useState, useEffect, useCallback } from "react";
import { useParams } from "react-router-dom";
import { css } from '@emotion/react';
import { getTicketApi, updateTicketAssigneesApi, updateTicketStatusApi, updateTicketTypeApi, updateTicketPriorityApi } from "./api/TicketApiService";
import { DescriptionComponent, NewCommentComponent, CommentComponent } from "./InlineTextareaComponent";
import InlineTextFieldComponent from "./InlineTextFieldComponent";
import TrackerDropzoneComponent from "./TrackerDropzoneComponent";
import Select from 'react-select';
import ShowErrorModal from "./ErrorModalComponent";
import { useAuth } from "./security/AuthContext";
import { Role } from "./RoleComponent";

const commentStyle = css({
	backgroundColor: '#fffde3',
	textAlign: 'left'
});

export default function TicketSingleViewComponent() {

	const authContext = useAuth();
	const [project, setProject] = useState('');
	const [issueName, setIssueName] = useState('');
	const [description, setDescription] = useState('');
	const [fileList, setFileList] = useState([]);
	const [assignees, setAssignees] = useState([]);
	const [selectedAssignees, setSelectedAssignees] = useState([]);
	const [comments, setComments] = useState([]);
	const [selectedStatus, setSelectedStatus] = useState('');
	const [statuses, setStatuses] = useState([]);
	const [selectedType, setSelectedType] = useState('');
	const [types, setTypes] = useState([]);
	const [selectedPriority, setSelectedPriority] = useState('');
	const [priorities, setPriorities] = useState([]);
	const isAdminOrUser = authContext.roles.some(role => [Role.ADMIN, Role.USER].includes(role));

	const { id } = useParams();

	const loadTicketData = useCallback(() => {
		getTicketApi(id)
			.then(response => {
				const responseData = response.data;
				const responseTicketData = responseData.ticketData;

				setProject(responseTicketData.project.projectName);
				setIssueName(responseTicketData.issueName);
				setDescription(responseTicketData.description);
				setFileList(responseTicketData.files);
				setComments(responseTicketData.comments);

				setAssignees(responseData.potentialAssignees.map(x => ({ label: x.username, value: x.id })));
				setStatuses(responseData.potentialStatuses.map(x => ({ label: x.status, value: x.id })));
				setTypes(responseData.potentialTypes.map(x => ({ label: x.typeName, value: x.id })));
				setPriorities(responseData.potentialPriorities.map(x => ({ label: x.priority, value: x.id })));

				setSelectedAssignees(responseTicketData.assignees.map(x => ({ label: x.username, value: x.id })));
				setSelectedStatus({ 'label': responseTicketData.ticketStatus.status, 'value': responseTicketData.ticketStatus.id });
				setSelectedType({ 'label': responseTicketData.ticketType.typeName, 'value': responseTicketData.ticketType.id });
				setSelectedPriority({ 'label': responseTicketData.ticketPriority.priority, 'value': responseTicketData.ticketPriority.id });

				authContext.setNotifications(responseData.notifications);
			})
			.catch(error => authContext.handleModalError(error.response.data));
	}, [id]);

	function handleAssigneeChange(assignee) {

		const assigneesDto = assignee.map(x => ({ username: x.label, id: x.value }));

		updateTicketAssigneesApi(id, assigneesDto)
			.then(
				setSelectedAssignees(assignee)
			)
			.catch(error => authContext.handleModalError(error.response.data));
	}

	function handleStatusChange(status) {

		const ticketStatusDto = {
			ticketStatusId: status.value
		};

		updateTicketStatusApi(id, ticketStatusDto)
			.then(
				setSelectedStatus(status)
			)
			.catch(error => authContext.handleModalError(error.response.data));
	}

	function handleTypeChange(type) {

		const ticketTypeDto = {
			ticketTypeId: type.value
		};

		updateTicketTypeApi(id, ticketTypeDto)
			.then(
				setSelectedType(type)
			)
			.catch(error => authContext.handleModalError(error.response.data));
	}

	function handlePriorityChange(priority) {

		const ticketPriorityDto = {
			ticketPriorityId: priority.value
		};

		updateTicketPriorityApi(id, ticketPriorityDto)
			.then(
				setSelectedPriority(priority)
			)
			.catch(error => authContext.handleModalError(error.response.data));
	}

	useEffect(() => { loadTicketData() }, [loadTicketData]);

	return (
		<div className="container">
			<ShowErrorModal modalShow={authContext.modalShow} setModalShow={authContext.setModalShow} modalErrorMessage={authContext.modalErrorMessage} />
			<h2>{project}</h2>
			<InlineTextFieldComponent defaultValue={issueName} ticketId={id} isUpdate={isAdminOrUser} />
			<DescriptionComponent defaultValue={description} ticketId={id} isUpdate={isAdminOrUser} />
			<p>Assignees:</p>
			<Select isMulti
				value={selectedAssignees}
				options={assignees}
				className="basic-multi-select my-5"
				classNamePrefix="select"
				placeholder="Assignees"
				onChange={(assignee) => handleAssigneeChange(assignee)}
				isDisabled={!isAdminOrUser} />
			<p>Ticket status:</p>
			<Select
				value={selectedStatus}
				options={statuses}
				className="basic-multi-select my-5"
				classNamePrefix="select"
				placeholder="Status"
				onChange={(status) => handleStatusChange(status)}
				isDisabled={!isAdminOrUser} />
			<p>Ticket type:</p>
			<Select
				value={selectedType}
				options={types}
				className="basic-multi-select my-5"
				classNamePrefix="select"
				placeholder="Type"
				onChange={(type) => handleTypeChange(type)}
				isDisabled={!isAdminOrUser} />
			<p>Ticket priority:</p>
			<Select
				value={selectedPriority}
				options={priorities}
				className="basic-multi-select my-5"
				classNamePrefix="select"
				placeholder="Priority"
				onChange={(priority) => handlePriorityChange(priority)}
				isDisabled={!isAdminOrUser} />
			<TrackerDropzoneComponent files={fileList} setFiles={setFileList} ticketId={id} isUpdate={true} isDisabled={!isAdminOrUser} />
			<NewCommentComponent ticketId={id} setComments={setComments} />
			<div>
				<label>Comments</label>
				<div>
					{comments.map((comment, index) => {
						return (
							<div key={index} css={commentStyle}>
								<CommentComponent comment={comment} allComments={comments} setComments={setComments}></CommentComponent>
							</div>
						);
					})}
				</div>
			</div>
		</div>
	);
}