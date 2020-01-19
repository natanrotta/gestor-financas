/*
    https://codeseven.github.io/toastr/demo.html
    yarn add toastr
*/
import toastr from 'toastr';

toastr.options = {
  "closeButton": true,
  "debug": false,
  "newestOnTop": true,
  "progressBar": true,
  "positionClass": "toast-top-right",
  "preventDuplicates": false,
  "onclick": null,
  "showDuration": "300",
  "hideDuration": "1000",
  "timeOut": "5000",
  "extendedTimeOut": "1000",
  "showEasing": "swing",
  "hideEasing": "linear",
  "showMethod": "fadeIn",
  "hideMethod": "fadeOut"
}

//Todos esses métodos são responsáveis por gerenciar as mensagens para o user.

export function mostrarMensagem(titulo, mensagem, tipo){
    toastr[tipo](mensagem, titulo)
}

export function mensagemErro(mensagem){
    mostrarMensagem('Erro', mensagem, 'error')
}

export function mensagemSucesso(mensagem){
    mostrarMensagem('Sucesso', mensagem, 'success')
}


export function mensagemAlerta(mensagem){
    mostrarMensagem('Alerta', mensagem, 'warnig')
}