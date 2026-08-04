import HeroBanner from "../components/HeroBanner";
import BranchesSection from "../components/BranchesSection";
import GallerySection from "../components/GallerySection";
import ServicesSection from "../components/ServicesSection";


const Home = () => {
  return (
    <div className="space-y-20">
      <HeroBanner />
      <ServicesSection />
      <BranchesSection />
      <GallerySection />
    </div>
  );
}

export default Home;