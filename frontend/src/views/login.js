import React from 'react';

import Card from '../components/card';
import FormGroup from '../components/form-group';
import { withRouter } from 'react-router-dom';

import UsuarioService from '../app/service/usuarioService';
import { mensagemErro } from '../components/toastr';
import { AuthContext } from '../main/provedorAutenticacao';

class Login extends React.Component {

    state = {
        email: '',
        senha: ''
    }

    constructor() {
        super();
        this.service = new UsuarioService()
    }

    //Cria requisição Post para nosso servidor
    //Client Http baseado em promises - chamada assíncrona
    entrar = () => {
        this.service.autenticar({
            email: this.state.email,
            senha: this.state.senha
        }).then(response => {
            //Salva o objeto do usuario dentro do storage do navegador
            this.context.iniciarSessao(response.data);
            this.props.history.push('/home') //manda para tela Home
        }).catch(erro => {
            mensagemErro("Erro ao fazer login")
        });
    }

    prepareCadastrar = () => {
        this.props.history.push('/cadastro-usuarios');
    }

    render() {
        return (
            <div className="row">
                <div className="col-md-6" style={{ position: 'relative', left: '300px' }}>
                    <div className="bs-docs-section">
                        <Card title="Login">
                            <div className="row">
                                <div className="col-lg-12">
                                    <div className="bs.component">
                                        <fieldset>
                                            <FormGroup label="Email *" htmlFor="exempleInputEmail">
                                                <input type="email"
                                                    value={this.state.email}
                                                    onChange={e => this.setState({ email: e.target.value })}
                                                    className="form-control"
                                                    id="exempleInputEmail"
                                                    aria-describedby="emailHelp"
                                                    placeholder="Digite seu Email" />
                                            </FormGroup>
                                            <FormGroup label="Senha *" htmlFor="exempleInputPassword">
                                                <input type="password"
                                                    value={this.state.senha}
                                                    onChange={e => this.setState({ senha: e.target.value })}
                                                    className="form-control"
                                                    id="exempleInputPassword"
                                                    aria-describedby="passwordHelp"
                                                    placeholder="Digite sua senha" />
                                            </FormGroup>
                                            <button
                                                onClick={this.entrar}
                                                className="btn btn-success">
                                                <i className="pi pi-sign-in"></i>
                                                Entrar
                                            </button>
                                            <button
                                                onClick={this.prepareCadastrar}
                                                className="btn btn-danger">
                                                <i className="pi pi-plus"></i>
                                                Cadastrar
                                             </button>
                                        </fieldset>
                                    </div>
                                </div>
                            </div>
                        </Card>
                    </div>
                </div>
            </div>
        )
    }
}

//Estou fazendo com que meu componente de classe se inscreva na minha autenticação
Login.contextType = AuthContext;

export default withRouter(Login);