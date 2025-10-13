import './App.css'
import {Container} from 'react-bootstrap';
import {BrowserRouter as Router,Route,Link,Routes} from 'react-router-dom';
import Home from "./pages/Home.jsx";
import TodoList from "./pages/TodoList.jsx";

function App() {
  return (
    <Container>
      <h1>CS491</h1>
      <Router>
        <Link to="/">   Home  </Link>
        <Link to="/todolist">   Todo List  </Link>
        <hr></hr>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/todolist" element={<TodoList />} />
          <Route path="*" element={<h1>404: page not found</h1>} />
        </Routes>
      </Router>
    </Container>
  )
}

export default App