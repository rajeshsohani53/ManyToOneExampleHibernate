// All calls to the Java backend live here.
// Paths start with /api, which Vite forwards to http://localhost:8080 (see vite.config.js).

async function request(path, options = {}) {
  const res = await fetch(path, {
    headers: { 'Content-Type': 'application/json' },
    ...options,
  })
  if (!res.ok) {
    const body = await res.json().catch(() => ({}))
    throw new Error(body.error || `Request failed (${res.status})`)
  }
  return res.status === 204 ? null : res.json()
}

export const getBrands = () => request('/api/brands')

export const createBrand = (name) =>
  request('/api/brands', { method: 'POST', body: JSON.stringify({ name }) })

export const getCars = () => request('/api/cars')

export const createCar = (car) =>
  request('/api/cars', { method: 'POST', body: JSON.stringify(car) })

export const deleteCar = (id) => request(`/api/cars/${id}`, { method: 'DELETE' })
