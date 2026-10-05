import Navbar from "../components/organisms/Navbar";
import Footer from "../components/organisms/Footer";

export default function MainLayout({ children }: { children: React.ReactNode }) {
    return (
        <div className="min-h-screen flex flex-col bg-gray-50">
            {/* Header */}
            <header>
                <Navbar />
            </header>

            {/* Main content */}
            <main
                className="
          flex-1
          px-4 sm:px-6 lg:px-8
          py-6 sm:py-8
        "
                role="main"
            >
                {children}
            </main>

            {/* Footer */}
            <footer>
                <Footer />
            </footer>
        </div>
    );
}
