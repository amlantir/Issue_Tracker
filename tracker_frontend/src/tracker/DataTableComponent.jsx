import DataTable from 'react-data-table-component-with-filter';

const defaultPaginationPerPage = 15;

export default function DataTableComponent(props) {
	return (
		<DataTable
			pagination
			paginationPerPage={defaultPaginationPerPage}
			striped
			highlightOnHover
			{...props}
		/>
	);
}