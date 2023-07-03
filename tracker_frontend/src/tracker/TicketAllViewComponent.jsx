import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { getAllTicketsApi, getAllTicketsByNameApi, deleteTicketApi } from "./api/TicketApiService";
import { useAuth } from "./security/AuthContext";
import DataTableComponent from "./DataTableComponent";
import { FcLowPriority, FcMediumPriority, FcHighPriority } from "react-icons/fc";
import ShowErrorModal from "./ErrorModalComponent";
import { formatDate } from "./api/Utility"
import { Role } from "./RoleComponent";
import { HiOutlineXMark } from "react-icons/hi2";
import Button from 'react-bootstrap/Button';

const prioritySize = 30;

function getPriority(priority) {
	switch (priority) {
		case 1:
			return <FcLowPriority size={prioritySize} />;
		case 2:
			return <FcMediumPriority size={prioritySize} />;
		case 3:
			return <FcHighPriority size={prioritySize} />;
		case 4:
			return <p><FcHighPriority size={prioritySize} /><FcHighPriority size={prioritySize} /></p>
		default:
			return <FcLowPriority size={prioritySize} />;
	}
}

const conditionalCellStyles = [
	{
		when: row => row.ticketType.id === 1,
		style: {
			color: 'red'
		},
	},
	{
		when: row => row.ticketType.id === 2,
		style: {
			color: 'blue'
		},
	},
	{
		when: row => row.ticketType.id === 3,
		style: {
			color: 'green'
		},
	},
];

const conditionalRowStyles = [
	{
		when: row => !row.isSeen,
		style: {
			backgroundColor: 'orange',
		},
	},
];

export default function TicketAllViewComponent() {

	const authContext = useAuth();

	const navigate = useNavigate();

	const [data, setData] = useState([]);

	const [dataFetched, setDataFetched] = useState(false);

	const [isAllTickets, setIsAllTickets] = useState(false);

	const isAdmin = authContext.roles.includes(Role.ADMIN);

	const columns = [
		{
			name: 'Project name',
			selector: row => row.project.projectName,
			sortable: true,
			filterable: true,
		},
		{
			name: 'Issue Name',
			selector: row => row.issueName,
			sortable: true,
			filterable: true,
		},
		{
			name: 'Created By',
			selector: row => row.createdBy,
			sortable: true,
			filterable: true,
		},
		{
			name: 'Type',
			selector: row => row.ticketType.typeName,
			sortable: true,
			filterable: true,
			conditionalCellStyles: conditionalCellStyles
		},
		{
			name: 'Created',
			selector: row => formatDate(row.createdAt),
			sortable: true,
			filterable: true,
		},
		{
			name: 'Priority',
			selector: row => getPriority(row.ticketPriority.id),
		},
		isAdmin && {
			name: 'Delete',
			selector: row => deleteTicketButton(row.id)
		}
	];

	function deleteTicketButton(ticketId) {
		return <Button className='btn btn-sm bg-danger rounded-circle' onClick={() => deleteTicket(ticketId)}>
			<HiOutlineXMark className='mb-1' />
		</Button>;
	}

	function deleteTicket(ticketId) {
		deleteTicketApi(ticketId, isAllTickets)
			.then(response => {
				setData(response.data);
			})
			.catch(error => authContext.handleModalError(error.response.data));
	}

	function getAllTicketsByName() {
		getAllTicketsByNameApi()
			.then(response => {
				setDataFetched(true);
				setData(response.data);
				setIsAllTickets(false);
			})
			.catch(error => authContext.handleModalError(error.response.data));
	}

	function getAllTickets() {
		getAllTicketsApi()
			.then(response => {
				setDataFetched(true);
				setData(response.data);
				setIsAllTickets(true);
			})
			.catch(error => authContext.handleModalError(error.response.data));
	}

	useEffect(() => getAllTicketsByName(), []);

	return (
		<div className="container">
			<Button className="me-5" onClick={() => getAllTickets()}>All tickets</Button><Button className="ms-5" onClick={() => getAllTicketsByName()}>My tickets</Button>
			<ShowErrorModal modalShow={authContext.modalShow} setModalShow={authContext.setModalShow} modalErrorMessage={authContext.modalErrorMessage} />
			<DataTableComponent
				columns={columns}
				data={data}
				onRowClicked={rowData => { navigate(`/tickets/${rowData.id}`); }}
				progressPending={!dataFetched}
				title="Tickets"
				conditionalRowStyles={conditionalRowStyles}
			/>
		</div>
	);
}