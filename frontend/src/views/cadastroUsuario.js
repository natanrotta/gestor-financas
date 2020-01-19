import React from 'react'
import { withRouter } from 'react-router-dom';

import Card from '../components/card';
import FormGroup from '../components/form-group';

import UsuarioService from '../app/service/usuarioService';
import { mensagemErro, mensagemSucesso } from '../components/toastr';

class CadastroUsuario extends React.Component {

    state = {
        nome: '',
        email: '',
        senha: '',
        senhaRepeticao: ''
    }

    constructor() {
        super();
        this.service = new UsuarioService();
    }

    cadastrar = () => {
        const { nome, email, senha, senhaRepeticao } = this.state;
        const usuario = { nome, email, senha, senhaRepeticao }

        try {
            this.service.validar(usuario);
        } catch (erro) {
            const msgs = erro.mensagens;
            msgs.forEach(msg => mensagemErro(msg));
            return false;
        }

        this.service.salvar(usuario)
            .then(response => {
                mensagemSucesso('Usuário cadastrado com sucesso!')
                this.props.history.push('/login');
            }).catch(error => {
                mensagemErro("Erro") //mudei
            });
    }

    cancelar = () => {
        this.props.history.push('/login');
    }

    render() {
        return (
            <Card title="Cadastro de Usuário">
                <div className="row">
                    <div className="col-lg-12">
                        <div className="bs.component">
                            <FormGroup label="Nome *" htmlFor="inputNome">
                                <input type="text"
                                    className="form-control"
                                    id="inputNome"
                                    name="nome"
                                    onChange={e => this.setState({ nome: e.target.value })}
                                    placeholder="Digite seu Nome" />
                            </FormGroup>
                            <FormGroup label="Email *" htmlFor="inputEmail">
                                <input type="email"
                                    className="form-control"
                                    id="inputEmail"
                                    name="email"
                                    onChange={e => this.setState({ email: e.target.value })}
                                    placeholder="Digite seu Email" />
                            </FormGroup>
                            <FormGroup label="Senha *" htmlFor="inputSenha">
                                <input type="password"
                                    className="form-control"
                                    id="inputSenha"
                                    name="senha"
                                    onChange={e => this.setState({ senha: e.target.value })}
                                    placeholder="Digite sua Senha" />
                            </FormGroup>
                            <FormGroup label="Repete Senha *" htmlFor="inputSenhaRepeticao">
                                <input type="password"
                                    className="form-control"
                                    id="inputSenhaRepeticao"
                                    name="senhaRepeticao"
                                    onChange={e => this.setState({ senhaRepeticao: e.target.value })}
                                    placeholder="Digite sua Senha" />
                            </FormGroup>
                            <button onClick={this.cadastrar} className="btn btn-success"> <i className="pi pi-save"> </i>Cadastrar</button>
                            <button onClick={this.cancelar} className="btn btn-danger"> <i className="pi pi-times"> </i>Cancelar</button>
                        </div>
                    </div>
                </div>
            </Card>
        )
    }
}

export default withRouter(CadastroUsuario);