import { useState } from 'react';
import './App.css'

function App() {
  const [input, setInput] = useState<number>(0);
  const [result, setResult] = useState<number | null>(null);

  const handleCalculate = async () => {
    try {
      const response = await fetch(`${import.meta.env.VITE_BACKEND_URL}/api/calculate?input=${input}`);
      if (response.ok) {
        const data = await response.json();
        setResult(data);
      } else {
        console.error("Error en la API");
      }
    } catch (error) {
      console.error("Error de red", error);
    }
  };

  return (<>
    <h1>UnderFast</h1>
    <div>
      <h1>Example calculation request</h1>
      <input
        type="number"
        value={input}
        onChange={(e) => setInput(Number(e.target.value))}
      />
      <button onClick={handleCalculate}>Calculate</button>
      {result !== null && <p>Result: {result}</p>}
    </div>
  </>
  )
}

export default App
