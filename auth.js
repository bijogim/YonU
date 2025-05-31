// auth.js
export async function checkLoginAndRedirect(callback) {
  try {
    const response = await fetch('http://localhost:8080/api/logged_in', {
      method: 'GET',
      credentials: 'include'
    });

    if (!response.ok) throw new Error('로그인 실패');

    const user = await response.json();
    if (!user || !user.email) throw new Error('사용자 정보 없음');

    // 로그인 상태일 때 콜백 실행
    callback(user);
  } catch (err) {
    console.warn('🚫 로그인 안됨, login.html로 이동합니다.');
    window.location.href = 'login.html';
  }
}
