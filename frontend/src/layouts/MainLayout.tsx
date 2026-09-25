import Navbar from "../components/organisms/Navbar";
import Footer from "../components/organisms/Footer";

export default function MainLayout({ children }: { children: React.ReactNode }) {
    return (
        <>
            <Navbar />

            <main className="min-h-screen px-4 py-6">
                {children}
            </main>

            <Footer />
        </>
    );
}
