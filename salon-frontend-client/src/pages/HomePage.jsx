import HeroBannerSection from "../components/HeroBannerSection";
import BranchesSection from "../components/BranchesSection";
import GallerySection from "../components/GallerySection";
import ServicesSection from "../components/ServicesSection";
import FooterSection  from "../components/FooterSection";


const HomePage = () => {
  return (
    <div className="space-y-20">
      <HeroBannerSection />
      <ServicesSection />
      <BranchesSection />
      <GallerySection />
      <FooterSection/>
    </div>
  );
}

export default HomePage;