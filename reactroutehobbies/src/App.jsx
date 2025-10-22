import './App.css'
import {Container,Navbar,Nav, Button} from 'react-bootstrap';
import {BrowserRouter as Router,Route,Link,Routes} from 'react-router-dom';
import Home from "./components/Home.jsx";
import Hobbies from "./components/Hobbies.jsx";
import About from "./components/About.jsx";
import Running from "./components/Running.jsx";
import GameDev from "./components/GameDev.jsx";
import Programming from "./components/Programming.jsx";
import Anime from "./components/Anime.jsx";

function App() {
  return (
    <Router>
      <h1>Hobbies</h1>
      <Navbar bg="dark" variant="dark" expand="md" collapseOnSelect>
        <Container>
          <Navbar.Collapse id="basic-navbar-nav">
            <Nav className="me-auto">
              <Button><Nav.Link as={Link} to="/">Home</Nav.Link></Button>
              <Button><Nav.Link as={Link} to="/hobbies">Hobbies</Nav.Link></Button>
              <Button><Nav.Link as={Link} to="/about">About</Nav.Link></Button>
            </Nav>
          </Navbar.Collapse>
        </Container>
      </Navbar>
      <Container>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/hobbies" element={<Hobbies />} />
          <Route path="/about" element={<About />} />
          <Route path="/running" element={<Running />} />
          <Route path="/gamedev" element={<GameDev />} />
          <Route path="/programming" element={<Programming />} />
          <Route path="/anime" element={<Anime />} />
          <Route path="*" element={<h1>404: page not found</h1>} />
        </Routes>
      </Container>
    </Router>
  )
}

export default App