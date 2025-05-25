# Example: Simple Inertia.js + Quarkus App with CSRF Protection

This example shows how to create a simple Inertia.js application with automatic CSRF protection.

## 1. Add the Extension

```xml
<dependency>
    <groupId>com.gurtus</groupId>
    <artifactId>inertia-quarkus</artifactId>
    <version>0.2.1</version>
</dependency>
```

**That's it!** CSRF protection is automatically configured. No additional dependencies or configuration needed.

## 2. Basic Configuration

```properties
# application.properties
quarkus.inertia.root-template=app.html
quarkus.inertia.version=1.0

# Optional: Development CORS (for Vite dev server)
%dev.quarkus.http.cors=true
%dev.quarkus.http.cors.origins=http://localhost:5173
```

## 3. Create Your Template

```html
<!-- src/main/resources/templates/app.html -->
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>My Inertia App</title>
    <script type="module" src="http://localhost:5173/main.tsx"></script>
  </head>
  <body>
    <div id="app" data-page="{pageData}"></div>
  </body>
</html>
```

## 4. Create Your Controller

```java
@Path("/")
public class ContactController extends InertiaController {

    @GET
    public Response form() {
        return inertia("ContactForm", Map.of(
            "message", "Send us a message!"
        ));
    }

    @POST
    @Path("/contact")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response submit(
        @FormParam("name") String name,
        @FormParam("email") String email,
        @FormParam("message") String message
    ) {
        // Process the form data
        System.out.println("Received: " + name + " - " + email + " - " + message);

        return inertia("ContactForm", Map.of(
            "success", "Message sent successfully!",
            "message", "Send us another message!"
        ));
    }
}
```

## 5. Create Your React Component

```tsx
// frontend/Pages/ContactForm.tsx
import React from "react";
import { Head, useForm } from "@inertiajs/react";

interface Props {
  message: string;
  success?: string;
}

export default function ContactForm({ message, success }: Props) {
  const { data, setData, post, processing, errors } = useForm({
    name: "",
    email: "",
    message: "",
  });

  const submit = (e: React.FormEvent) => {
    e.preventDefault();
    // CSRF token is automatically included by Axios!
    post("/contact");
  };

  return (
    <>
      <Head title="Contact Form" />

      <div className="max-w-md mx-auto mt-8 p-6 bg-white rounded-lg shadow-md">
        <h1 className="text-2xl font-bold mb-4">Contact Us</h1>

        <p className="mb-4 text-gray-600">{message}</p>

        {success && (
          <div className="mb-4 p-3 bg-green-100 border border-green-400 text-green-700 rounded">
            {success}
          </div>
        )}

        <form onSubmit={submit} className="space-y-4">
          <div>
            <label className="block text-sm font-medium text-gray-700">
              Name
            </label>
            <input
              type="text"
              value={data.name}
              onChange={(e) => setData("name", e.target.value)}
              className="mt-1 block w-full rounded-md border-gray-300 shadow-sm"
              required
            />
          </div>

          <div>
            <label className="block text-sm font-medium text-gray-700">
              Email
            </label>
            <input
              type="email"
              value={data.email}
              onChange={(e) => setData("email", e.target.value)}
              className="mt-1 block w-full rounded-md border-gray-300 shadow-sm"
              required
            />
          </div>

          <div>
            <label className="block text-sm font-medium text-gray-700">
              Message
            </label>
            <textarea
              value={data.message}
              onChange={(e) => setData("message", e.target.value)}
              rows={4}
              className="mt-1 block w-full rounded-md border-gray-300 shadow-sm"
              required
            />
          </div>

          <button
            type="submit"
            disabled={processing}
            className="w-full bg-blue-600 text-white py-2 px-4 rounded-md hover:bg-blue-700 disabled:opacity-50"
          >
            {processing ? "Sending..." : "Send Message"}
          </button>
        </form>
      </div>
    </>
  );
}
```

## 6. Frontend Setup

```json
// package.json
{
  "dependencies": {
    "@inertiajs/react": "^1.0.0",
    "react": "^18.0.0",
    "react-dom": "^18.0.0"
  },
  "devDependencies": {
    "@vitejs/plugin-react": "^4.0.0",
    "vite": "^5.0.0"
  }
}
```

```tsx
// main.tsx
import { createInertiaApp } from "@inertiajs/react";
import { createRoot } from "react-dom/client";

createInertiaApp({
  resolve: (name) => {
    const pages = import.meta.glob("./Pages/**/*.tsx", { eager: true });
    return pages[`./Pages/${name}.tsx`];
  },
  setup({ el, App, props }) {
    createRoot(el).render(<App {...props} />);
  },
});
```

## What You Get Automatically

✅ **CSRF Protection**: Automatic CSRF tokens with Axios integration  
✅ **Form Handling**: Secure form submissions without manual token management  
✅ **Error Handling**: Built-in validation and error responses  
✅ **Asset Versioning**: Automatic cache busting  
✅ **Development Mode**: Hot reloading and CORS support

## Testing CSRF Protection

1. **Open Browser DevTools** → Application → Cookies
2. **See XSRF-TOKEN cookie** automatically set by Quarkus
3. **Submit the form** and check Network tab
4. **See X-XSRF-TOKEN header** automatically sent by Axios
5. **Try removing the cookie** and see the 400 error

No manual CSRF token handling required! 🎉
