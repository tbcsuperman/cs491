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
            picture: "https://picsum.photos/id/69/4912/3264",
            link: "/gamedev",
        },
        {
            title: "Programming",
            picture: "https://picsum.photos/id/5/5000/3334",
            link: "/programming",
        },
        {
            title: "Anime",
            picture: "https://static0.srcdn.com/wordpress/wp-content/uploads/2024/10/demon-slayer-roar-of-victory-poster.jpg?w=1200&h=675&fit=crop",
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