import React, { useState } from "react";

import '../../style/Login.css';

const SignUpForm = () => {
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [email, setEmail] = useState("");
  const [username, setUsername] = useState("");
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");

  return (
    <div>
      
    </div>
  )
}

// interface SignUpFormData {
//   firstName: string;
//   lastName: string;
//   username: string;
//   email: string;
//   phoneNumber: string;
//   password: string;
//   confirmPassword: string;
// }

// const SignUpForm = () => {
//   const [formData, setFormData] = useState<SignUpFormData>({
//     firstName: "",
//     lastName: "",
//     username: "",
//     email: "",
//     phoneNumber: "",
//     password: "",
//     confirmPassword: "",
//   });
//   const [formErrors, setFormErrors] = useState<string[]>([]);

//   const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
//     setFormData({
//       ...formData,
//       [event.target.name]: event.target.value,
//     });
//   };

//   const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
//     event.preventDefault();

//     const errors = [];

//     if (!formData.firstName) {
//       errors.push("First name is required");
//     }

//     if (!formData.lastName) {
//       errors.push("Last name is required");
//     }

//     if (!formData.username) {
//       errors.push("Username is required");
//     }

//     if (!formData.email) {
//       errors.push("Email is required");
//     }

//     if (!formData.phoneNumber) {
//       errors.push("Phone number is required");
//     }

//     if (!formData.password) {
//       errors.push("Password is required");
//     }

//     if (!formData.confirmPassword) {
//       errors.push("Please confirm your password");
//     }

//     if (formData.password !== formData.confirmPassword) {
//       errors.push("Passwords do not match");
//     }

//     if (errors.length > 0) {
//       setFormErrors(errors);
//     } else {
//       setFormErrors([]);
//       console.log(formData); // do something with form data here
//     }
//   };

//   return (
//     <form onSubmit={handleSubmit}>
//       {formErrors.length > 0 && (
//         <div>
//           {formErrors.map((error) => (
//             <div key={error}>{error}</div>
//           ))}
//         </div>
//       )}
//       <div>
//         <label htmlFor="firstName">First Name</label>
//         <input
//           type="text"
//           id="firstName"
//           name="firstName"
//           value={formData.firstName}
//           onChange={handleInputChange}
//         />
//       </div>
//       <div>
//         <label htmlFor="lastName">Last Name</label>
//         <input
//           type="text"
//           id="lastName"
//           name="lastName"
//           value={formData.lastName}
//           onChange={handleInputChange}
//         />
//       </div>
//       <div>
//         <label htmlFor="username">Username</label>
//         <input
//           type="text"
//           id="username"
//           name="username"
//           value={formData.username}
//           onChange={handleInputChange}
//         />
//       </div>
//       <div>
//         <label htmlFor="email">Email</label>
//         <input
//           type="email"
//           id="email"
//           name="email"
//           value={formData.email}
//           onChange={handleInputChange}
//         />
//       </div>
//       <div>
//         <label htmlFor="phoneNumber">Phone Number</label>
//         <input
//           type="tel"
//           id="phoneNumber"
//           name="phoneNumber"
//           value={formData.phoneNumber}
//           onChange={handleInputChange}
//         />
//       </div>
//       <div>
//         <label htmlFor="password">Password</label>
//         <input
//           type="password"
//           id="password"
//           name="password"
//           value={formData.password}
//           onChange={handleInputChange}
//           />
//         </div>
//       <div>
//         <label htmlFor="confirmPassword">Phone Number</label>
//         <input
//           type="password"
//           id="confirmPassword"
//           name="confirmPassword"
//           value={formData.confirmPassword}
//           onChange={handleInputChange}
//         />
//       </div>
//     </form>)
// };

export default SignUpForm;