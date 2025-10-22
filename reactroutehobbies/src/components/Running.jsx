import '../App.css'

let Running = () => {
    return (
        <div>
            <h1>Running</h1>
            <img src="https://picsum.photos/id/177/2515/1830" height="300"/><br/>
            <h2>Introduction</h2>
            <p>
                One of my favorite hobbies is to run.
                I am an active runner who runs about 4-5 times a week totaling about 25-30 miles per week.
                I have been running for 6 years, and have been endurance running (5K and beyond) for 3 years.
            </p>
            <h2>Personal Bests</h2>
            <table>
                <tr>
                    <th>Distance</th>
                    <th>Record</th>
                </tr>
                <tr>
                    <td>Mile</td>
                    <td>5:52</td>
                </tr>
                <tr>
                    <td>5K</td>
                    <td>19:42</td>
                </tr>
                <tr>
                    <td>10K</td>
                    <td>43:45</td>
                </tr>
                <tr>
                    <td>HM</td>
                    <td>1:41:40</td>
                </tr>
            </table>
        </div>
    )
}

export default Running