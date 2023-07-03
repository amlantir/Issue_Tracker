/** @jsxImportSource @emotion/react */

import { useState, useEffect } from 'react';
import { css } from '@emotion/react';
import Textfield from '@atlaskit/textfield';
import { token } from '@atlaskit/tokens';
import InlineEdit from '@atlaskit/inline-edit';
import { updateTicketIssueNameApi } from './api/TicketApiService';
import ShowErrorModal from "./ErrorModalComponent";
import { useAuth } from "./security/AuthContext";

const fontSize = 35;
const gridSize = token('space.100', '8px');

const readViewContainerStyles = css({
	display: 'inline',
	maxWidth: '100%',
	minHeight: `${(gridSize * 2.5) / fontSize}em`,
	padding: `${token('space.100', '8px')} ${token('space.075', '6px')}`,
	fontSize: `${fontSize}px`,
	lineHeight: `${(gridSize * 2.5) / fontSize}`,
	wordBreak: 'break-word',
	fontWeight: 'bold',
	textAlign: 'center'
});

export default function InlineTextFieldComponent({ defaultValue, ticketId, isUpdate }) {

	const authContext = useAuth();
	const [editValue, setEditValue] = useState('');

	useEffect(() => { setEditValue(defaultValue); }, [defaultValue]);

	function updateTicketDescription() {
		const ticketUpdateDto = {
			issueName: editValue
		};

		updateTicketIssueNameApi(ticketId, ticketUpdateDto)
			.then(() => {
			})
			.catch((error) => authContext.handleModalError(error.response.data));
	}

	return (
		<div
			style={{
				padding: `${gridSize}px ${gridSize}px ${gridSize * 6}px`,
			}}
			className=''
		>
			<ShowErrorModal modalShow={authContext.modalShow} setModalShow={authContext.setModalShow} modalErrorMessage={authContext.modalErrorMessage} />
			{isUpdate ?
				<InlineEdit
					defaultValue={editValue}
					label="Issue"
					editView={({ errorMessage, ...fieldProps }) => (
						<Textfield {...fieldProps} autoFocus onChange={(e) => setEditValue(e.currentTarget.value)} />
					)}
					readView={() => (
						<div css={readViewContainerStyles} data-testid="read-view">
							{editValue}
						</div>
					)}
					onConfirm={(value) => updateTicketDescription(value)}
				/>
				:
				<div><p css={readViewContainerStyles}>{editValue}</p></div>
			}
		</div>
	);
};