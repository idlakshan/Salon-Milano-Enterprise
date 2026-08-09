import HeroBannerSection from "../components/home/HeroBannerSection";
import BranchesSection from "../components/home/BranchesSection";
import GallerySection from "../components/home/GallerySection";
import ServicesSection from "../components/home/ServicesSection";
import FooterSection  from "../components/home/FooterSection";


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