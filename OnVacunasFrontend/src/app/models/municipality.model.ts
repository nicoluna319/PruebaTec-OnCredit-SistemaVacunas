export interface Municipality {
    id: number;
    name: string;
    departmentName: string;
    // Otros campos si es necesario
  }
  
  export interface MunicipalityResponse {
    content: Municipality[];
    municipalities: any[];
    // Otros campos si es necesario
  }

