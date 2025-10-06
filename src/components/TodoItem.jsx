import React from 'react';
import {Button} from 'react-bootstrap';

let TodoItem = (props) => {
    const id = props.id;

    return (
        <div>
            {props.text}
            <Button onClick={() => {props.deleteTodoItem(id)}}>-</Button>
        </div>
    )
}

export default TodoItem