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
            <table class="center-table">
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
                    <td>Half Marathon</td>
                    <td>1:41:40</td>
                </tr>
            </table>
            <h2>2025 New Year's Resolutions</h2>
            <ul class="center-table">
                <li>Run 1,000 miles</li>
                <li>Run a sub-5:50 mile</li>
                <li>Run a sub-20 5K &#9989;</li>
                <li>Run a sub-1:40 half marathon</li>
            </ul>
            <h2>Lifetime Goals</h2>
            <table class="center-table">
                <tr>
                    <th>Phase</th>
                    <th>Goal</th>
                    <th>Timeframe</th>
                </tr>
                <tr>
                    <td>Year</td>
                    <td>Sub-20 5K</td>
                    <td>2025 &#9989;</td>
                </tr>
                <tr>
                    <td>Short Term</td>
                    <td>Sub-40 10K</td>
                    <td>2027</td>
                </tr>
                <tr>
                    <td>Long Term</td>
                    <td>Sub-3:00 Marathon</td>
                    <td>2030</td>
                </tr>
                <tr>
                    <td>Lifetime</td>
                    <td>Sub-2:50 Marathon</td>
                    <td>2030s</td>
                </tr>
            </table>
        </div>
    )
}

export default Running