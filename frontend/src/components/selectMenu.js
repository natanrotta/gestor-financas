import React from 'react';

function SelectMenu(props) {

    const options = props.lista.map( (option, index) => {
        return (
            <option key={index} value={option.value}>
                {option.label}
            </option>
        )
    });

    return (
        //pega todas as propriedades do componente e coloca aqui 
        <select {...props} >
            {options}
        </select>
    );
}

export default SelectMenu;