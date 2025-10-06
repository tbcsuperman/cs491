import React, {useState} from 'react';

let Counter2 = () => {
    const [counter, setCount] = useState(0);
    return (
        <div>
            <h2>Counter2</h2>
            <div><button onClick={() => setCount((count) => count - 1)}>-</button></div>
            <div>contar es {counter}</div>
            <div><button onClick={() => setCount((count) => count + 1)}>+</button></div>
        </div>
    )
}

export default Counter2