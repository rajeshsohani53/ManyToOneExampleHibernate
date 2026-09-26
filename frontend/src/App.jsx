import { useEffect, useState } from 'react'
import * as api from './api'

const emptyCar = { name: '', color: '', price: '', fuleType: 'Petrol', brandId: '' }

export default function App() {
  const [cars, setCars] = useState([])
  const [brands, setBrands] = useState([])
  const [brandName, setBrandName] = useState('')
  const [car, setCar] = useState(emptyCar)
  const [error, setError] = useState('')

  async function loadData() {
    try {
      const [b, c] = await Promise.all([api.getBrands(), api.getCars()])
      setBrands(b)
      setCars(c)
      setError('')
    } catch (e) {
      setError(`Could not reach the backend. Is ApiServer running on port 8080? (${e.message})`)
    }
  }

  useEffect(() => {
    loadData()
  }, [])

  async function handleAddBrand(e) {
    e.preventDefault()
    try {
      await api.createBrand(brandName)
      setBrandName('')
      loadData()
    } catch (e) {
      setError(e.message)
    }
  }

  async function handleAddCar(e) {
    e.preventDefault()
    try {
      await api.createCar({
        ...car,
        price: car.price === '' ? null : Number(car.price),
        brandId: Number(car.brandId),
      })
      setCar(emptyCar)
      loadData()
    } catch (e) {
      setError(e.message)
    }
  }

  async function handleDelete(id) {
    try {
      await api.deleteCar(id)
      loadData()
    } catch (e) {
      setError(e.message)
    }
  }

  const updateCar = (field) => (e) => setCar({ ...car, [field]: e.target.value })

  return (
    <main>
      <h1>Cars &amp; Brands</h1>
      <p className="subtitle">React → Javalin REST API → Hibernate → MySQL (many cars to one brand)</p>

      {error && <p className="error">{error}</p>}

      <section className="forms">
        <form onSubmit={handleAddBrand}>
          <h2>Add brand</h2>
          <input placeholder="Brand name" value={brandName} onChange={(e) => setBrandName(e.target.value)} required />
          <button type="submit">Add brand</button>
        </form>

        <form onSubmit={handleAddCar}>
          <h2>Add car</h2>
          <input placeholder="Car name" value={car.name} onChange={updateCar('name')} required />
          <input placeholder="Color" value={car.color} onChange={updateCar('color')} />
          <input placeholder="Price" type="number" step="any" value={car.price} onChange={updateCar('price')} />
          <select value={car.fuleType} onChange={updateCar('fuleType')}>
            <option>Petrol</option>
            <option>Diesel</option>
            <option>CNG</option>
            <option>Electric</option>
          </select>
          <select value={car.brandId} onChange={updateCar('brandId')} required>
            <option value="">Select brand…</option>
            {brands.map((b) => (
              <option key={b.id} value={b.id}>
                {b.name} (#{b.id})
              </option>
            ))}
          </select>
          <button type="submit">Add car</button>
        </form>
      </section>

      <h2>All cars</h2>
      <div className="table-wrap">
        <table>
          <thead>
            <tr>
              <th>ID</th>
              <th>Name</th>
              <th>Color</th>
              <th>Price</th>
              <th>Fuel</th>
              <th>Brand</th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            {cars.length === 0 && (
              <tr>
                <td colSpan="7" className="empty">No cars yet</td>
              </tr>
            )}
            {cars.map((c) => (
              <tr key={c.id}>
                <td>{c.id}</td>
                <td>{c.name}</td>
                <td>{c.color}</td>
                <td>{c.price}</td>
                <td>{c.fuleType}</td>
                <td>{c.brand ? c.brand.name : <em>none</em>}</td>
                <td>
                  <button className="danger" onClick={() => handleDelete(c.id)}>Delete</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </main>
  )
}
