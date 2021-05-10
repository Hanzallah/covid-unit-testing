import React, { Component } from 'react';
import styles from './login.css';

class Login extends Component {
	constructor(props) {
		super(props);
		this.state = {
			login: true
		};

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
			fetch("http://localhost:8080/api/v1/user/login", {
				method: "POST",
				mode: 'cors',
				headers: {
					'Content-Type': 'application/json',
					'Accepts': 'application/json',
				},
				body: JSON.stringify({
					name: '',
					email: this.state.email,
					password: this.state.password,
				})
			}).then(async response => {
				let res = await response.json();
				if (res.code === '1') {
					this.props.onComplete(res.payload);
				}
			})
				.catch(err => {
					console.log(err);
				});
		} else {
			fetch("http://localhost:8080/api/v1/user/register", {
				method: "POST",
				mode: 'cors',
				headers: {
					'Content-Type': 'application/json',
					'Accepts': 'application/json',
				},
				body: JSON.stringify({
					name: this.state.name,
					age: parseInt(this.state.age),
					gender: this.state.sex,
					city: this.state.city,
					country: this.state.country,
					email: this.state.email,
					password: this.state.password,
				})
			}).then(async response => {
				let res = await response.json();
				console.log(res)
				if (res.code === '1') {
					this.props.onComplete(res.payload);
				}
			})
				.catch(err => {
					console.log(err);
				});
		}
	}

	render() {
		return (
			<div className="container">
				<div className="box"></div>
				<div className="container-forms">
					<div className="container-info">
						<div className="info-item">
							<div className="table">
								<div className="table-cell">
									{/* Don't remove this block, it maintains shape. */}
								</div>
							</div>
						</div>
						<div className="info-item">
							<div className="table">
								<div className="table-cell">
									{this.state.login ?
										<div>
											<p>Don't have an account?</p>
											<div className="btn" onClick={() => this.setState({ login: false })}>
												Sign up
											</div>
										</div>
										:
										<div>
											<p>Have an account?</p>
											<div className="btn" onClick={() => this.setState({ login: true })}>
												Log in
											</div>
										</div>
									}
								</div>
							</div>
						</div>
					</div>
					<div className="container-form">
						<div className="form-item log-in">
							<div className="table">
								<div className="table-cell">
									{this.state.login ?
										<div>
											<input name="email" placeholder="Email" type="text" onChange={(e) => this.handleChange(e)} />
											<input name="password" placeholder="Password" type="Password" onChange={(e) => this.handleChange(e)} />
											<div className="btn" onClick={() => this.handleUser()}>
												{this.state.login ? 'Log in' : 'Sign up'}
											</div>
										</div>
										:
										<div>
											<input name="name" placeholder="Name" type="text" onChange={(e) => this.handleChange(e)} />
											<input name="password" placeholder="Password" type="Password" onChange={(e) => this.handleChange(e)} />
											<input name="age" placeholder="Age" type="number" onChange={(e) => this.handleChange(e)} />
											<input name="country" placeholder="Country" type="text" onChange={(e) => this.handleChange(e)} />
											<input name="city" placeholder="City" type="text" onChange={(e) => this.handleChange(e)} />
											<input name="email" placeholder="Email" type="text" onChange={(e) => this.handleChange(e)} />
											<select name="sex" onClick={(e) => this.handleChange(e)}>
												<option value='F'>Female</option>
												<option value='M'>Male</option>
											</select>
											<div className="btn" onClick={() => this.handleUser()}>
												{this.state.login ? 'Log in' : 'Sign up'}
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

export default Login;
