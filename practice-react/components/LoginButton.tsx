"use client";

import { useState } from "react";
import { auth } from "@/lib/firebase";
import { signInWithEmailAndPassword } from "firebase/auth";

export default function LoginButton() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const onLogin = async () => {
    try {
      // 1️⃣ Firebase 이메일/비밀번호 로그인
      const cred = await signInWithEmailAndPassword(
        auth,
        email,
        password
      );

      // 2️⃣ Firebase ID Token (Bearer Token)
      const idToken = await cred.user.getIdToken();

      // 3️⃣ 백엔드 보호 API 호출
      const res = await fetch("http://localhost:8080/api/protected", {
        method: "GET",
        headers: {
          Authorization: `Bearer ${idToken}`,
        },
      });

      const text = await res.text();
      alert(text);
    } catch (err: any) {
      alert(err.message);
    }
  };

  return (
    <div style={{ display: "flex", flexDirection: "column", gap: 8, width: 240 }}>
      <input
        type="email"
        placeholder="email"
        value={email}
        onChange={(e) => setEmail(e.target.value)}
      />

      <input
        type="password"
        placeholder="password"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
      />

      <button
        onClick={onLogin}
        style={{ padding: 12, border: "1px solid #ccc", borderRadius: 8 }}
      >
        로그인
      </button>
    </div>
  );
}