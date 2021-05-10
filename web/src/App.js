import React, { Component } from 'react';
import Login from './components/login/login';
import SymptomsForm from './components/symptomsForm/symptomsForm';
import './App.css';

class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      login: true,
      user: {},
    };
  }

  complete = user => {
    console.log(user)
    this.setState({
      login: false,
      user: user
    })
  }

  render() {
    return (
      <div className='main' >
        {this.state.login ?
          <Login onComplete={(user) => this.complete(user)} />
          :
          <SymptomsForm
            onBack={() => this.setState({ login: true })}
            user={this.state.user}
          />
        }
      </div>
    );
  }
}

export default App;
