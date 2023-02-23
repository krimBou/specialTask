import { API_URL } from "./const";

export const getData = async () => {
  try {
    const res = await fetch(API_URL);
    const data = await res.json();
    return data;
  } catch (error) {
    console.log(error);
  }
};
