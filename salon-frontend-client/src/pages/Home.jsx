import HeroBanner from "../components/HeroBanner";
import BranchesSection from "../components/BranchesSection";
import GallerySection from "../components/GallerySection";
import ServicesSection from "../components/ServicesSection";
import FooterSection  from "../components/FooterSection";


const Home = () => {
  return (
    <div className="space-y-20">
      <HeroBanner />
      <ServicesSection />
      <BranchesSection />
      <GallerySection />
      <FooterSection/>
    </div>
  );
}

export default Home;