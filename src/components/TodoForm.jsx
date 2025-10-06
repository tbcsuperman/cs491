import React,{useState,useRef} from 'react';
import {Button} from 'react-bootstrap';

let TodoForm = (props) => {
  return (
    <div>
      <input type="text" placeholder="Enter a task" ref={props.ref} />
      <Button onClick={props.addTodoItem}>+</Button>
    </div>
  )
}

export default TodoForm;