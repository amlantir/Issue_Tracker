
export function convertToFormData(data) {
	const json = JSON.stringify(data);
	const blob = new Blob([json], {
		type: 'application/json'
	});

	return blob;
}

export function formatDate(date) {

	const dateFormat = new Intl.DateTimeFormat('hu-HU', {
		year: 'numeric',
		hour12: false,
		month: '2-digit',
		day: '2-digit',
		hour: '2-digit',
		minute: '2-digit',
		second: '2-digit',
	});

	const formattedDate = dateFormat.format(new Date(date));

	return formattedDate;
}