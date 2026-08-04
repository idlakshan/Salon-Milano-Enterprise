import HeroBanner from "../components/HeroBanner";
import ServicesSection from "../components/ServicesSection";
import BranchesSection from "../components/BranchesSection";
import GallerySection from "../components/GallerySection";

export const Home = () => {
  return (
    <div className="space-y-20">
      <HeroBanner />
      <ServicesSection />
      <BranchesSection />
      <GallerySection />
    </div>
  );
}