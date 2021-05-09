import React, { Component } from 'react';
import Chart from '../chart/chart';
import styles from './symptomsForm.css';

class SymptomsForm extends Component {
	constructor(props) {
		super(props);
		this.state = {

		};
	}

	componentDidMount() {
		//fetch data from api
		this.setState({
			name: 'Jane Doe',
			age: 21,
			country: 'Turkey',
			city: 'Ankara',
			email: 'jane.doe@mail.com',
		})
	}


	handleChange = e => {
		let fieldName = e.target.name;
		let value = (e.target.value);
		this.setState({
			[fieldName]: value
		})
	}

	handleUser = () => {
		if (this.state.login) {
			//call login service
		} else {
			//call sign up service
		}
		//if return is successful
		this.props.onComplete()
	}

	submit = () => {
		//send symptoms from state to service
		this.setState({ enterSymptoms: false })
	}

	render() {
		return (
			<div className="container">
				<div className="box"></div>
				<div className="container-forms">
					<div className="container-form" style={this.state.showSymptoms ? {width: '530px'} : null}>
						<div className="form-item log-in">
							<div className="table">
								<div className="table-cell">
									{!this.state.enterSymptoms && !this.state.showSymptoms &&
										<div>
											<input name="name" value={this.state.name} disabled />
											<input name="age" value={this.state.age} disabled />
											<input name="country" value={this.state.country} disabled />
											<input name="city" value={this.state.city} disabled />
											<input name="email" value={this.state.email} disabled />
											<div className="btn" style={{ width: 'fit-content' }} onClick={() => this.setState({ enterSymptoms: true })}>
												Enter Today's Symptoms
											</div>
											<div className="btn" style={{ width: 'fit-content' }} onClick={() => this.setState({ showSymptoms: true })}>
												View History
											</div>
											<div className="btn" style={{ width: 'fit-content' }} onClick={this.props.onBack}>
												Log out
											</div>
										</div>
									}
									{this.state.enterSymptoms &&
										<div>
											<div className='label'>Body Temperature:</div>
											<input name="temp" placeholder="Temperature" type="number" onChange={(e) => this.handleChange(e)} />
											<div className='label'>Coughing:</div>
											<select name="cough" onClick={(e) => this.handleChange(e)}>
												<option value={true}>Yes</option>
												<option value={false}>No</option>
											</select>
											<div className='label'>Fatigue:</div>
											<select name="fatigue" onClick={(e) => this.handleChange(e)}>
												<option value={true}>Yes</option>
												<option value={false}>No</option>
											</select>
											<div className='label'>Difficulty Breathing:</div>
											<select name="breathing" onClick={(e) => this.handleChange(e)}>
												<option value={true}>Yes</option>
												<option value={false}>No</option>
											</select>
											<div className="btn" onClick={() => this.submit()}>
												Submit
											</div>
											<div className="btn" style={{ width: 'fit-content' }} onClick={() => this.setState({ enterSymptoms: false })}>
												Back
											</div>
										</div>
									}
									{this.state.showSymptoms &&
										<div>
											<Chart />
											<div className="btn" style={{ width: 'fit-content' }} onClick={() => this.setState({ showSymptoms: false })}>
												Back
											</div>
										</div>
									}
								</div>
							
							</div>
						</div>
					</div>
				
				</div>
			</div>
		);
	}
}

export default SymptomsForm;
