/** @jsxImportSource @emotion/react */

import { useState, useEffect } from 'react';
import { css } from '@emotion/react';
import TextArea from '@atlaskit/textarea';
import { fontSize as getFontSize } from '@atlaskit/theme/constants';
import { token } from '@atlaskit/tokens';
import InlineEdit from '@atlaskit/inline-edit';
import { updateTicketDescriptionApi } from './api/TicketApiService';
import { createCommentApi, updateCommentApi, deleteCommentApi } from "./api/CommentApiService";
import Button from 'react-bootstrap/Button';
import { formatDate } from "./api/Utility";
import { HiOutlineXMark } from "react-icons/hi2";
import ShowErrorModal from "./ErrorModalComponent";
import { useAuth } from "./security/AuthContext";

const fontSize = getFontSize();
const gridSize = token('space.100', '8px');
const minRows = 2;
const textAreaLineHeightFactor = 2.5;

const readViewContainerStyles = css({
	minHeight: `${gridSize * textAreaLineHeightFactor * minRows}px`,
	padding: token('space.075', '6px'),
	lineHeight: `${(gridSize * textAreaLineHeightFactor) / fontSize}`,
	wordBreak: 'break-word',
	whiteSpace: 'pre-line',
});

const commentDetailsStyle = css({
	margin: '0',
	fontSize: '13px'
});

const userNameStyle = css({
	fontWeight: 'bold'
});

const floatButtonStyle = css({
	float: 'right'
});

export function DescriptionComponent({ defaultValue, ticketId, isUpdate }) {

	const authContext = useAuth();
	const [editValue, setEditValue] = useState('');

	useEffect(() => { setEditValue(defaultValue); }, [defaultValue]);

	function updateTicketDescription() {
		const ticketUpdateDto = {
			description: editValue
		};

		updateTicketDescriptionApi(ticketId, ticketUpdateDto)
			.catch((error) => authContext.handleModalError(error.response.data));
	}

	return (
		<div
			style={{
				padding: `${gridSize}px ${gridSize}px ${gridSize * 6}px`,
				width: '100%',
			}}
			className='mt-4 mb-5'
		>
			<ShowErrorModal modalShow={authContext.modalShow} setModalShow={authContext.setModalShow} modalErrorMessage={authContext.modalErrorMessage} />

			{isUpdate ?
				<InlineEdit
					defaultValue={editValue}
					label="Description"
					editView={({ errorMessage, ...fieldProps }, ref) => (
						<TextArea {...fieldProps} ref={ref} onChange={(e) => setEditValue(e.currentTarget.value)} />
					)}
					readView={() => (
						<div css={readViewContainerStyles}>
							{editValue}
						</div>
					)}
					onConfirm={updateTicketDescription}
					keepEditViewOpenOnBlur
					readViewFitContainerWidth
				/>
				:
				<div><p css={readViewContainerStyles}>{editValue}</p></div>
			}


		</div>
	);
};

export function NewCommentComponent({ ticketId, setComments }) {

	const authContext = useAuth();
	const [editValue, setEditValue] = useState('');

	function createComment() {

		const createCommentDto = {
			ticketId: ticketId,
			ticketComment: editValue
		};

		createCommentApi(createCommentDto)
			.then((response) => {
				setEditValue('');
				setComments(comments => comments.concat(response.data));
			})
			.catch((error) => authContext.handleModalError(error.response.data));
	}

	return (
		<div
			style={{
				padding: `${gridSize}px ${gridSize}px ${gridSize * 6}px`,
				width: '100%',
			}}
			className='my-5'
		>
			<ShowErrorModal modalShow={authContext.modalShow} setModalShow={authContext.setModalShow} modalErrorMessage={authContext.modalErrorMessage} />
			<TextArea
				resize="auto"
				maxHeight="20vh"
				name="area"
				placeholder='New comment'
				className='my-5'
				onChange={(e) => setEditValue(e.currentTarget.value)}
				value={editValue}
			/>
			<Button onClick={createComment} className='bg-success'>Send comment</Button>
		</div>
	);
};

export function CommentComponent({ comment, allComments, setComments }) {

	const authContext = useAuth();
	const username = authContext.username;

	const [editValue, setEditValue] = useState('');

	useEffect(() => { setEditValue(comment.ticketComment); }, [comment.ticketComment]);

	function updateComment() {

		const updateCommentDto = {
			ticketComment: editValue
		};

		updateCommentApi(comment.id, updateCommentDto)
			.then((response) => {
				const newComments = allComments.filter(x => x.id !== response.data.id);
				newComments.push(response.data);
				newComments.sort((a, b) => (a.createdAt > b.createdAt) ? 1 : -1);
				setComments(newComments);
			})
			.catch((error) => authContext.handleModalError(error.response.data));
	}

	function deleteComment() {
		deleteCommentApi(comment.id)
			.then(() => {
				const newComments = allComments.filter(x => x.id !== comment.id);
				newComments.sort((a, b) => (a.createdAt > b.createdAt) ? 1 : -1);
				setComments(newComments);
			})
			.catch((error) => authContext.handleModalError(error.response.data));
	}

	return (
		<div
			style={{
				padding: `${gridSize}px ${gridSize}px ${gridSize * 6}px`,
				width: '100%',
			}}
			className='mt-3 mb-5'
		>
			<ShowErrorModal modalShow={authContext.modalShow} setModalShow={authContext.setModalShow} modalErrorMessage={authContext.modalErrorMessage} />
			{comment.createdBy === username &&
				<Button className='btn btn-sm bg-danger mb-5 rounded-circle' css={floatButtonStyle} onClick={deleteComment}>
					<HiOutlineXMark className='mb-1' />
				</Button>}
			<p css={commentDetailsStyle}>by: <span css={userNameStyle}>{comment.modifiedBy}</span></p>
			<p css={commentDetailsStyle}>created: {formatDate(comment.createdAt)}</p>
			<p css={commentDetailsStyle}>last modified: {formatDate(comment.updatedAt)}</p>
			{comment.createdBy === username ?
				<InlineEdit
					defaultValue={editValue}
					editView={({ errorMessage, ...fieldProps }, ref) => (
						<TextArea {...fieldProps} ref={ref} onChange={(e) => setEditValue(e.currentTarget.value)} />
					)}
					readView={() => (
						<div css={readViewContainerStyles}>
							{editValue}
						</div>
					)}
					onConfirm={updateComment}
					keepEditViewOpenOnBlur
					readViewFitContainerWidth
				/>
				:
				<div css={readViewContainerStyles} className='mt-3'>{editValue}</div>
			}
		</div>
	);
};