import React,{useState} from 'react';
import TodoForm from './TodoForm';
import TodoItem from './TodoItem';

let TodoParent = () => {
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

    let makeTodoItem = (todoItem) => (
        <TodoItem
            key = {todoItem.id}
            id = {todoItem.id}
            text = {todoItem.text}
            deleteTodoItem = {deleteTodoItem}
        />
    );

    return (
        <div>
            <h1>Todo List</h1>
            <h2>Add Todo</h2>
            <TodoForm addTodoItem={addTodoItem} ref={ref}></TodoForm>
            <h2>Todo List</h2>
            <div>{list.map(makeTodoItem)}</div>
        </div>
    )
}

export default TodoParent;