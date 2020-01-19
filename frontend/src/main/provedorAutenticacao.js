import React from 'react';

import AuthService from '../app/service/authService';

export const AuthContext = React.createContext();
export const AuthConsumer = AuthContext.Consumer;

const AuthProvider = AuthContext.Provider;

class ProvedorAutenticacao extends React.Component {

    state = {
        usuarioAutenticado: null,
        isAutenticado: false
    }

    iniciarSessao = (usuario) => {
        AuthService.logar(usuario);
        this.setState({ isAutenticado: true, usuarioAutenticado: usuario });
    }

    encerrarSessao = () => {
        AuthService.removerUsuarioAutenticado();
        this.setState({ isAutenticado: false, usuarioAutenticado: null });
    }

    render() {

        const contexto = {
            usuarioAutenticado: this.state.usuarioAutenticado,
            isAutenticado: this.state.isAutenticado,
            iniciarSessao: this.iniciarSessao,
            encerrarSessao: this.encerrarSessao
        }

        //Divido com todos os meus filhos, os meus estados/contexto
        //Tipo um F5
        return (
            <AuthProvider value={ contexto }>
                {this.props.children}
            </AuthProvider>
        )
    }
}

export default ProvedorAutenticacao;