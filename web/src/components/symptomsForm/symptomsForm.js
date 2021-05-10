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
		this.setState({
			user: this.props.user,
		})
	}


	handleChange = e => {
		let fieldName = e.target.name;
		let value = (e.target.value);
		this.setState({
			[fieldName]: value
		})
	}

	submit = () => {
		fetch(`http://localhost:8080/api/v1/symptoms/create/${this.state.user?.id}`, {
			method: "POST",
			mode: 'cors',
			headers: {
				'Content-Type': 'application/json',
				'Accepts': 'application/json',
			},
			body: JSON.stringify({
				fever: this.state.temp,
				cough: this.state.cough,
				tiredness: this.state.tiredness,
				difficultyBreathing: this.state.breathing
			})
		}).then(async response => {
			let res = await response.json();
			if (res.code === '1') {
				console.log('success')
			}
		}).catch(err => {
			console.log(err);
		})
	}

	render() {
		return (
			<div className="container" >
				<div className="box"></div>
				<div className="container-forms">
					<div className="container-form" style={this.state.showSymptoms ? { width: '530px' } : null}>
						<div className="form-item log-in">
							<div className="table">
								<div className="table-cell">
									{!this.state.enterSymptoms && !this.state.showSymptoms &&
										<div>
											<input name="name" value={this.state.user?.name} disabled />
											<input name="age" value={this.state.user?.age} disabled />
											<input name="country" value={this.state.user?.country} disabled />
											<input name="city" value={this.state.user?.city} disabled />
											<input name="email" value={this.state.user?.email} disabled />
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
											<select name="tiredness" onClick={(e) => this.handleChange(e)}>
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
											<Chart userId={this.state.user?.id} />
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
