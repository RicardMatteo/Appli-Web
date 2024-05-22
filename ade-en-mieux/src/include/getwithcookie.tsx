import Cookies from "js-cookie";

async function invokeGetWithCookie(
  method: string,
  successMsg: string,
  failureMsg: string
): Promise<any | null> {
  const authToken = Cookies.get("authToken") || ""; // Check if authToken cookie exists, provide default value if it doesn't
  const requestOptions: RequestInit = {
    method: "GET",
    headers: {
      "Content-Type": "application/json; charset=utf-8",
      cookie: authToken,
    },
    credentials: "include",
  };

  try {
    const res = await fetch("/ADEenMieux/rest/" + method, requestOptions);
    if (res.ok) {
      console.log(successMsg);
      console.log(res);
    } else {
      console.log(failureMsg);
    }
    return await res.json();
  } catch (error) {
    console.error("Error in invokeGetWithCookie :", error);
  }
  throw new Error("Error in invokeGetWithCookie");
}

export { invokeGetWithCookie };