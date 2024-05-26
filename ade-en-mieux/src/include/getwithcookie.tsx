async function invokeGetWithCookie(
  method: string,
  successMsg: string,
  failureMsg: string,
  headerName?: string,
  headerValue?: string
): Promise<any | null> {
  const requestOptions: RequestInit = {
    method: "GET",
    headers: {
      "Content-Type": "application/json; charset=utf-8",
      ...(headerName && headerValue && { [headerName]: headerValue }),
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
