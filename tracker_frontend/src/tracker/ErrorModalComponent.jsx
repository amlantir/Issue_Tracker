import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';

function MyVerticallyCenteredModal(props) {
	return (
		<Modal
			show={props.show}
			onHide={props.onHide}
			size="lg"
			aria-labelledby="contained-modal-title-vcenter"
			centered
		>
			<Modal.Header closeButton>
				<Modal.Title id="contained-modal-title-vcenter">
					Unknown Error
				</Modal.Title>
			</Modal.Header>
			<Modal.Body>
				<h4>{props.modalErrorMessage}</h4>
			</Modal.Body>
			<Modal.Footer>
				<Button onClick={props.onHide}>Close</Button>
			</Modal.Footer>
		</Modal>
	);
}

export default function ShowErrorModal({ modalShow, setModalShow, modalErrorMessage }) {
	return (
		<MyVerticallyCenteredModal
			show={modalShow}
			onHide={() => setModalShow(false)}
			modalErrorMessage={modalErrorMessage}
		/>
	);
}