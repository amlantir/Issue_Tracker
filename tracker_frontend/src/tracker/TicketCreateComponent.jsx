import { Field, Form, Formik } from "formik";
import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { createTicketApi, fillCreateTicketData } from "./api/TicketApiService";
import { convertToFormData } from "./api/Utility";
import { useAuth } from "./security/AuthContext";
import ShowErrorModal from "./ErrorModalComponent";
import TrackerDropzoneComponent from "./TrackerDropzoneComponent";
import Select from 'react-select';

export default function TicketCreateComponent() {

	const [projects, setProjects] = useState([]);
	const [issueName, setIssueName] = useState('');
	const [description, setDescription] = useState('');
	const [fileList, setFileList] = useState([]);
	const [assignees, setAssignees] = useState([]);
	const [selectedAssignees, setSelectedAssignees] = useState([]);
	const [types, setTypes] = useState([]);
	const [priorities, setPriorities] = useState([]);

	const authContext = useAuth();
	const navigate = useNavigate();

	function onSubmit(values) {
		const ticket = {
			projectId: values.project,
			issueName: values.issueName,
			description: values.description,
			assignees: selectedAssignees.map(x => ({ id: x.value })),
			typeId: values.type,
			priorityId: values.priority
		}

		const files = fileList ? [...fileList] : [];

		const body = new FormData();

		body.append("ticketCreateDto", convertToFormData(ticket));

		files.forEach((file, i) => {
			body.append('files', file, file.name);
		});

		createTicketApi(body)
			.then(() => {
				navigate(`/tickets`);
			})
			.catch((error) => authContext.handleModalError(error.response.data));
	}

	function fillCreateTicketDataSelects() {
		fillCreateTicketData()
			.then(response => {
				setProjects(response.data.projects);
				setAssignees(response.data.users.map(x => ({ label: x.username, value: x.id })));
				setTypes(response.data.types);
				setPriorities(response.data.priorities);
			})
			.catch(error => authContext.handleModalError(error.response.data));
	}

	useEffect(() => fillCreateTicketDataSelects(), []);

	return (
		<div className="container">
			<ShowErrorModal modalShow={authContext.modalShow} setModalShow={authContext.setModalShow} modalErrorMessage={authContext.modalErrorMessage} />
			<h1>Enter Ticket Details</h1>
			<div>
				<Formik initialValues={{ description, issueName }}
					enableReinitialize={true}
					onSubmit={onSubmit}
					validateOnChange={false}
					validateOnBlur={false}>
					{
						() => (
							<Form>
								<fieldset className="form-group">
									<label>Project</label>
									<Field as="select" className="form-control" name="project" defaultValue={'default'}>
										<option disabled value="default">Select a value</option>
										{projects.map((i, index) => (<option key={index} value={i.id}>{i.projectName}</option>))}
									</Field>
								</fieldset>
								<fieldset className="form-group">
									<label>Issue Name</label>
									<Field type="text" className="form-control" name="issueName" />
								</fieldset>
								<fieldset className="form-group">
									<label>Description</label>
									<Field as="textarea" className="form-control" name="description" />
								</fieldset>
								<fieldset className="form-group">
									<label>Assignees</label>
									<Select
										isMulti
										name="assignees"
										options={assignees}
										className="basic-multi-select"
										classNamePrefix="select"
										onChange={(assignees) => setSelectedAssignees(assignees)}
									/>
								</fieldset>
								<fieldset className="form-group">
									<label>Type</label>
									<Field as="select" className="form-control" name="type" defaultValue={'default'}>
										<option disabled value="default">Select a value</option>
										{types.map((i, index) => (<option key={index} value={i.ticketTypeId}>{i.typeName}</option>))}
									</Field>
								</fieldset>
								<fieldset className="form-group">
									<label>Priority</label>
									<Field as="select" className="form-control" name="priority" defaultValue={'default'}>
										<option disabled value="default">Select a value</option>
										{priorities.map((i, index) => (<option key={index} value={i.ticketPriorityId}>{i.priority}</option>))}
									</Field>
								</fieldset>
								<fieldset className="form-group">
									<label>Attachment</label>
									<TrackerDropzoneComponent files={fileList} setFiles={setFileList} isUpdate={false} />
								</fieldset>
								<div>
									<button className="btn btn-success m-5" type="submit">Save</button>
								</div>
							</Form>
						)
					}
				</Formik>
			</div>
		</div>
	);
}