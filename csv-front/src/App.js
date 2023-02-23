import "./App.css";
import { useEffect, useState } from "react";
import { getData } from "./getData";
import { API_URL } from "./const";

function App() {
  const [csvFile, setCsvFile] = useState();
  const [csvData, setCsvData] = useState([]);

  useEffect(() => {
    getCsvData();
  }, []);

  const getCsvData = () => {
    getData().then((data) => {
      setCsvData(data);
    });
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    if (!csvFile) {
      return;
    }
    const formData = new FormData();
    formData.append("file", csvFile);
    fetch(API_URL, {
      method: "POST",
      body: formData,
    }).then(() => {
      getCsvData();
    });
  };

  const handleChange = (event) => {
    setCsvFile(event.target.files[0]);
    console.log("here is a file", csvFile);
  };

  return (
    <div className="App">
      <form onSubmit={handleSubmit}>
        <input type="file" onChange={handleChange} />
        <button>submit</button>
      </form>
      <table class="persons-table">
        <thead>
          <tr>
            <th>Name</th>
            <th>Email</th>
            <th>Phone number</th>
          </tr>
        </thead>
        <tbody>
          {csvData.map((row) => (
            <tr key={row.id}>
              <td>{row.name}</td>
              <td>{row.email}</td>
              <td>{row.phoneNumber}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default App;
