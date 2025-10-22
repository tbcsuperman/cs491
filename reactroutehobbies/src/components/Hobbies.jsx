import {BrowserRouter as Router,Route,Link,Routes} from 'react-router-dom';
import '../App.css'

let Hobbies = () => {
    const hobbyList = [
        {
            title: "Running",
            picture: "https://picsum.photos/id/177/2515/1830",
            link: "/running",
        },
        {
            title: "Game Development",
            picture: "https://picsum.photos/id/60/1920/1200",
            link: "/gamedev",
        },
        {
            title: "Programming",
            picture: "https://picsum.photos/id/5/5000/3334",
            link: "/programming",
        },
        {
            title: "Anime",
            picture: "https://picsum.photos/id/56/2880/1920",
            link: "/anime",
        },
    ];

    let makeHobbyItem = (hobbyItem) => {
        return (
            <div>
                <h1>{hobbyItem.title}</h1><br/>
                <Link to={hobbyItem.link}><img src={hobbyItem.picture} height="300"/></Link>
            </div>
        )
    };

    return (
        <div>
            {hobbyList.map(makeHobbyItem)}
        </div>
    )
}

export default Hobbies;