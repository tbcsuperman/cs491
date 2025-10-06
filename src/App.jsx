import './App.css'
import {Container} from 'react-bootstrap';
import {BrowserRouter as Router,Route,Link,Routes} from 'react-router-dom';
import TodoList from "./pages/TodoList.jsx"

function App() {
  return (
    <Container>
      <h1>CS491</h1>
      <Router>
        <ul>
          <li>
            <Link to="/todolist">Todo List</Link>
          </li>
        </ul>
        <Routes>
          <Route path="/todolist" element={<TodoList />} />
          <Route path="*" element={<h1>404: page not found</h1>} />
        </Routes>
      </Router>
    </Container>
  )
}

export default App