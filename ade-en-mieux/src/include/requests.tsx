/* eslint-disable @typescript-eslint/no-unused-vars */
import { createRoot } from "react-dom/client";

function ShowMessage(message: string): void {
    const container = document.getElementById("Message");
    const root = createRoot(container!);
    root.render(<p>{message}</p>);
  }

async function invokePost(
    method: string,
    data: any,
    successMsg: string,
    failureMsg: string
  ): Promise<void> {
    const requestOptions: RequestInit = {
      method: "POST",
      headers: { "Content-Type": "application/json; charset=utf-8" },
      body: JSON.stringify(data),
    };
  
    try {
      const res = await fetch("/ADEenMieux/rest/" + method, requestOptions);
      if (res.ok) {
        //ShowMessage(successMsg);
        console.log(successMsg);
      } else {
        console.log(failureMsg);
        //ShowMessage(failureMsg);
      }
    } catch (error) {
      console.error("Error in invokePost :", error);
    }
  }
  
  async function invokeGet(
    method: string,
    failureMsg: string
  ): Promise<any | null> {
    try {
      const res = await fetch("/ADEenMieux/rest/" + method);
      if (res.ok) {
        return await res.json();
      } else {
        ShowMessage(failureMsg);
        return null;
      }
    } catch (error) {
      console.error("Error in invokeGet :", error);
      return null;
    }
  }

export { invokePost, invokeGet, ShowMessage };