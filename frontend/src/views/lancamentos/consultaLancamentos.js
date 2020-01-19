import React from 'react';
import { withRouter } from 'react-router-dom'

import Card from '../../components/card';
import FormGroup from '../../components/form-group';
import SelectMenu from '../../components/selectMenu';
import LancamentosTable from './lancamentosTable';

import LancamentoService from '../../app/service/lancamentoService';
import LocalStorageService from '../../app/service/localStorageService';

import * as messages from '../../components/toastr';

import { Dialog } from 'primereact/dialog';
import { Button } from 'primereact/button';



class ConsultaLancamento extends React.Component {

    state = {
        ano: '',
        mes: '',
        tipo: '',
        descricao: '',
        mostraDialog: false,
        lancamentoDeletar: {},
        lancamentos: []
    }

    constructor() {
        super();
        this.service = new LancamentoService();
    }

    editar = (id) => {
        this.props.history.push(`/cadastro-lancamentos/${id}`)
    }


    abrirConfirmacao = (lancamento) => {
        this.setState({ mostraDialog: true, lancamentoDeletar: lancamento })
    }

    cancelarDelecao = () => {
        this.setState({ mostraDialog: false, lancamentoDeletar: {} })
    }

    deletar = () => {
        this.service
            .deletar(this.state.lancamentoDeletar.id)
            .then(response => {
                //Aqui deletamos o item excluido da nossa lista.
                const lancamentos = this.state.lancamentos;
                const index = lancamentos.indexOf(this.state.lancamentoDeletar)
                //recebe o index para ser deletado + a quantidade de linhas que será deletado.
                lancamentos.splice(index, 1)
                //atualiza.
                this.setState({ lancamentos: lancamentos, mostraDialog: false })
                messages.mensagemSucesso('Deletado.')
            }).catch(error => {
                messages.mensagemErro('Erro ao deletar!')
            });
    }

    buscar = () => {
        if (!this.state.ano) {
            messages.mensagemErro('o preenchimento do campo Ano é obrigatório.')
            return false;
        }
        const usuarioLogado = LocalStorageService.obterItem('_usuario_logado');

        const lancamentoFiltro = {
            ano: this.state.ano,
            mes: this.state.mes,
            tipo: this.state.tipo,
            descricao: this.state.descricao,
            usuario: usuarioLogado.id
        }

        this.service
            .consultar(lancamentoFiltro)
            .then(resposta => {
                const lista = resposta.data;
                if(lista.length < 1){
                    messages.mensagemAlerta("Nenhum resultado foi encontrado.")
                }
                this.setState({ lancamentos: lista })
            }).catch(error => {
                messages.mensagemErro('Erro ao buscar!')
            })


    }


    preparaFormulatioCadastro = () => {
        this.props.history.push('/cadastro-lancamentos');
    }

    alterarStatus = (lancamento, status) => {
        this.service
            .alterarStatus(lancamento.id, status)
            .then(response => {
                const lancamentos = this.state.lancamentos;
                const index = lancamentos.indexOf(lancamento);
                if (index !== -1) {
                    lancamento['status'] = status;
                    lancamentos[index] = lancamento;
                    this.setState({ lancamento });
                }
                messages.mensagemSucesso('Status atualizado com sucesso!')
            })
    }

    render() {

        const meses = this.service.obterListaMeses();
        const tipos = this.service.obterListaTipos();

        const confirmDialogfooter = (
            <div>
                <Button label="Sim" icon="pi pi-check" onClick={this.deletar} />
                <Button label="Não" icon="pi pi-times" onClick={this.cancelarDelecao} />
            </div>
        );

        return (
            <Card tittle="Consulta Lançamentos">
                <div className="row">
                    <div className="col-md-4">
                        <div className="bs-component">
                            <FormGroup label="Ano: *" htmlFor="inputAno">
                                <input
                                    type="text"
                                    className="form-control"
                                    id="inputAno"
                                    value={this.state.ano}
                                    onChange={e => this.setState({ ano: e.target.value })}
                                    placeholder="Digite o Ano"
                                />
                            </FormGroup>

                            <FormGroup label="Mês:" htmlFor="inputMes">
                                <SelectMenu
                                    id="inputMes"
                                    value={this.state.mes}
                                    onChange={e => this.setState({ mes: e.target.value })}
                                    className="form-control"
                                    lista={meses} />
                            </FormGroup>

                            <FormGroup label="Descrição: *" htmlFor="inputDescricao">
                                <input
                                    type="text"
                                    className="form-control"
                                    id="inputDescricao"
                                    value={this.state.descricao}
                                    onChange={e => this.setState({ descricao: e.target.value })}
                                    placeholder="Informe uma descrição"
                                />
                            </FormGroup>

                            <FormGroup label="Tipo Lançamento:" htmlFor="inputTipo">
                                <SelectMenu
                                    id="inputTipo"
                                    value={this.state.tipo}
                                    onChange={e => this.setState({ tipo: e.target.value })}
                                    className="form-control"
                                    lista={tipos} />
                            </FormGroup>

                            <button
                                onClick={this.buscar}
                                className="btn btn-success">
                                <i className="pi pi-search"></i>
                                Buscar
                            </button>
                            <button 
                                onClick={this.preparaFormulatioCadastro} 
                                className="btn btn-danger">
                                <i  className="pi pi-plus"></i>        
                                Cadastrar
                            </button>
                        </div>
                    </div>
                </div>
                <br />
                <div className="row">
                    <div className="col-md-12">
                        <div className="bs-component">
                            <LancamentosTable
                                lancamentos={this.state.lancamentos}
                                deleteAction={this.abrirConfirmacao}
                                editAction={this.editar}
                                alterarStatus={this.alterarStatus}

                            />
                        </div>
                    </div>
                </div>
                <div>
                    <Dialog header="Confirmação"
                        visible={this.state.mostraDialog}
                        style={{ width: '50vw' }}
                        footer={confirmDialogfooter}
                        modal={true}
                        onHide={() => this.setState({ mostraDialog: false })}>
                        Você realmente deseja deletar esse lançamento?
                    </Dialog>
                </div>
            </Card>
        );
    }


}

export default withRouter(ConsultaLancamento);