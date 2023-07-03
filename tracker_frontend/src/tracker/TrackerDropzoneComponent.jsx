import React, { useMemo } from 'react';
import { useDropzone } from 'react-dropzone';
import { AiOutlineFile } from 'react-icons/ai';
import { HiOutlineXMark } from "react-icons/hi2";
import Button from 'react-bootstrap/Button';
import { deleteAttachmentApi, downloadAttachmentApi, uploadAttachmentApi } from "./api/FileDataApiService";
import ShowErrorModal from "./ErrorModalComponent";
import { useAuth } from "./security/AuthContext";
import { Role } from './RoleComponent';

const thumbsContainer = {
	display: 'flex',
	flexDirection: 'row',
	flexWrap: 'wrap',
	marginTop: 16
};

const thumb = {
	display: 'flex',
	flexDirection: 'column',
	marginBottom: 8,
	marginRight: 50,
	width: 150,
	height: 100,
	padding: 4,
	wordWrap: 'break-word'
};

const baseStyle = {
	flex: 1,
	display: 'flex',
	flexDirection: 'column',
	alignItems: 'center',
	padding: '20px',
	borderWidth: 2,
	borderRadius: 2,
	borderColor: '#858585',
	borderStyle: 'dashed',
	backgroundColor: '#f0f0f0',
	color: '#606060',
	outline: 'none',
	transition: 'border .24s ease-in-out'
};

const focusedStyle = {
	borderColor: '#2196f3'
};

const acceptStyle = {
	borderColor: '#00e676'
};

const rejectStyle = {
	borderColor: '#ff1744'
};

export default function TrackerDropzoneComponent({ files, setFiles, ticketId, isUpdate, isDisabled }) {

	const authContext = useAuth();
	const isAdmin = authContext.roles.includes(Role.ADMIN);

	const onDrop = acceptedFiles => {

		if (isUpdate) {
			if (acceptedFiles.length === 0) {
				return;
			}

			const body = new FormData();
			body.append('files', ...acceptedFiles, acceptedFiles[0].name);

			uploadAttachmentApi(ticketId, body)
				.then(response => {
					setFiles(files => files.concat(response.data[0]));
				})
				.catch((error) => authContext.handleModalError(error.response.data));
		} else {
			setFiles(files => files.concat(...acceptedFiles));
		}
	};

	const fileValidator = (file) => {

		if (file.size > 26214400) {
			authContext.handleModalError("The file you're trying to upload exceeds the 25MB file limit!");
		}

	}

	const {
		getRootProps,
		getInputProps,
		isFocused,
		isDragAccept,
		isDragReject } = useDropzone({ onDrop, maxSize: 26214400, validator: fileValidator });

	const style = useMemo(() => ({
		...baseStyle,
		...(isFocused ? focusedStyle : {}),
		...(isDragAccept ? acceptStyle : {}),
		...(isDragReject ? rejectStyle : {})
	}), [
		isFocused,
		isDragAccept,
		isDragReject
	]);

	function deleteAttachment(id) {
		deleteAttachmentApi(id)
			.then(() => {
			})
			.catch((error) => authContext.handleModalError(error.response.data));
	}

	const removeFile = file => () => {
		if (isUpdate) {
			deleteAttachment(file.id);
		}
		const newFiles = [...files]
		newFiles.splice(newFiles.indexOf(file), 1);
		setFiles(newFiles);
	}

	const downloadAttachment = id => () => {
		if (isUpdate) {
			downloadAttachmentApi(id)
				.then((response) => {
					const filename = response.headers.get('Content-Disposition').split('filename=')[1];
					const url = window.URL.createObjectURL(response.data);
					let a = document.createElement('a');
					a.href = url;
					a.download = filename;
					a.click();
				})
				.catch((error) => authContext.handleModalError(error.response.data));
		}
	}

	const thumbs = files.map(file => (
		<div style={thumb} key={isUpdate ? file.id : file.name}>
			<div>
				<AiOutlineFile style={{ cursor: 'pointer' }} size={80} title={file.fileName} onClick={downloadAttachment(file.id)} />
				{isAdmin &&
					<Button className='btn btn-sm bg-danger mb-5 rounded-circle' onClick={removeFile(file)}>
						<HiOutlineXMark className='mb-1' />
					</Button>
				}
			</div>
			<p style={{ cursor: 'pointer' }} onClick={downloadAttachment(file.id)}>{isUpdate ? file.fileName : file.name}</p>
		</div>
	));

	return (
		<section className="container mb-5">
			<div>
				<ShowErrorModal modalShow={authContext.modalShow} setModalShow={authContext.setModalShow} modalErrorMessage={authContext.modalErrorMessage} />
				{
					isDisabled ?
						'' :
						<div {...getRootProps({ style })}>
							<input {...getInputProps()} />
							<p>Drag 'n' drop some files here, or click to select files</p>
						</div>
				}
				<aside style={thumbsContainer}>
					{thumbs}
				</aside>
			</div>
		</section>
	);
}