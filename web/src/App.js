import React, { Component } from 'react';
import Login from './components/login/login';
import SymptomsForm from './components/symptomsForm/symptomsForm';
import './App.css';

class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      login: true
    };

  }
  render() {
    return (
      <div className='main' >
        {this.state.login ?
          <Login onComplete={() => this.setState({ login: false })} />
          :
          <SymptomsForm onBack={() => this.setState({ login: true })}/>
        }
      </div>
    );
  }
}

export default App;
