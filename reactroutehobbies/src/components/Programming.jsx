import '../App.css'

let Programming = () => {
    return (
        <div>
            <h1>Programming</h1>
            <img src="https://picsum.photos/id/5/5000/3334" height="300"/><br/>
            <h2>Introduction</h2>
            <p>
                As a computer science major, I love programming.
                Particularly, I love the technical and theoretical part of programming.
                My strengths is in making stuff work rather than presentation.
                This is why software/web development is not one of my strong suits.
                I have very little interest in designing UI/UX.
                Instead, I focus more on the functionality of software/websites.
                I also have a keen interest in very specific fields of programming.
                These following sections describe these fields.
            </p>
            <h2>Circuit Design</h2>
            <p>
                I love working with circuits and logic.
                As you may know, computers are just complex circuits that uses logic to perform calculations.
                Modern CPUs have billions of transistors that execute instructions to run a program.
                I have created a circuit simulator where one can create circuits out of basic logic gates.
                It isn't a realistic circuit simulator as that would go down the field of electrical engineering.
                Once more, I focus more on the theoretical side of programming.
                Using my circuit simulator, I have built a full CPU out of logic gates.
            </p>
            <h2>CPU Emulation</h2>
            <p>
                Building up from Circuit Design, I love learning and experimenting with how CPUs work.
                Throughout my years of college, I have taught myself the intricacies of CPU architecture.
                Besides creating a CPU from logic gates, I have created many CPU emulators that emulate retro CPUs or even my own custom-made CPUs.
                To program for these CPUs, I have also learned assembly language.
                I know how to program in assembly for a fair variety of CPU architectures, like x86 and my own custom CPU.
            </p>
            <h2>Compiler Theory</h2>
            <p>
                My last interest builds upon the field of CPU architecture.
                To write code that the CPU can execute, we need a program that can convert a language that we can understand to the language that the CPU can understand.
                The CPU can only understand machine code but writing machine code is very cumbersome.
                So we have created programming languages to write code more easily.
                We have low level languages like assembly, which is just a version of machine code that we can understand.
                Then there are high level languages that are more abstract that we can understand better but the CPU cannot at all.
                To convert between these level of programming languages, we need a compiler.
                Throughout my years of playing around with CPU architectures, I needed a way to write code that the CPU can execute.
                So I wrote an assembler that can convert assembly into machine code.
                With each new version, I learned more about how compilers work and the fundamentals behind compiler theory.
                Right now, I am working on a self-hosting assembler for my custom CPU architecture, which is an assembly program for an assembler.
                In the future, I plan to write my own programming language for my custom CPU too.
            </p>
        </div>
    )
}

export default Programming