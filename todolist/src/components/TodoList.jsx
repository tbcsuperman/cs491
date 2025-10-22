// this is a merged version of all 3 files that i wrote first before splitting it into 3 files

import React,{useState} from 'react';
import {Button} from 'react-bootstrap';

let Todo = () => {
    const [list,setTodo] = useState([]);
    const [count,setCount] = useState(0);
    let ref = React.createRef();

    let addTodoItem = () => {
        setTodo([...list,{id:count,text:ref.current.value}]);
        setCount((count) => count + 1);
        console.log("added item " + ref.current.value + " as id " + count);
        ref.current.value = "";
    }

    let deleteTodoItem = (id) => {
        setTodo([...list.filter(item => item.id !== id)]);
        console.log("deleted item id " + id);
    }

    let makeTodoItem = (todoItem) => {
        const id = todoItem.id;
        return (
            <tr>
                <td>{todoItem.text}</td>
                <td><Button onClick={() => {deleteTodoItem(id)}}>-</Button></td>
            </tr>
        )
    };

    return (
        <div>
            <h1>Todo List</h1>
            <h2>Add Todo</h2>
            <div>
                <input type="text" placeholder="Enter a task" ref={ref} />
                <Button onClick={addTodoItem}>+</Button>
            </div>
            <h2>Todo List</h2>
            <table>
                <tbody>
                    {list.map(makeTodoItem)}
                </tbody>
            </table>
        </div>
    )
}

export default Todo;