const baseUrl = "http://localhost:8080/don-hang"
const USERNAME = "TH04383"  // MSSV của bạn
const PASSWORD = "SD20202"  // Lớp của bạn

export const fetchGetAllData = async() => {
    const response = await fetch(`${baseUrl}/hien-thi`, {
        headers: {
            "Authorization": "Basic " + btoa(`${USERNAME}:${PASSWORD}`)
        }
    })
    
    if(!response.ok){
        throw new Error(`HTTP ${response.status}`)
    }
    
    return response.json()
}