import { Phone, Mail, MapPin, Clock } from "lucide-react";

const FooterSection = () => {
  return (
    <footer className="bg-brand-dark-paper border-t border-brand-dark-border text-white pt-12 pb-8 px-6">
      <div className="max-w-7xl mx-auto">
        <div className="grid grid-cols-1 md:grid-cols-2 gap-10 pb-10 border-b border-brand-dark-border items-start">
          <div className="space-y-3">
            <h3 className="font-display text-2xl font-extrabold tracking-tight text-white">
              MILANO <span className="text-brand-red-light">SALON</span>
            </h3>
            <p className="text-xs md:text-base sm:text-sm text-brand-silver leading-relaxed max-w-md">
              Experience world-class luxury grooming and styling tailored to
              your unique personality. Visit any of our premier branches
              islandwide.
            </p>
          </div>

          <div className="space-y-3 md:justify-self-end">
            <h4 className="text-base font-bold uppercase tracking-wider text-brand-red-light">
              Headquarters
            </h4>

            <ul className="space-y-2.5 text-xs sm:text-sm text-brand-silver">
              <li className="flex items-center gap-2.5">
                <MapPin className="w-4 h-4 text-brand-red-light shrink-0" />
                <span>No. 42, Galle Road, Colombo 03, Sri Lanka</span>
              </li>
              <li className="flex items-center gap-2.5">
                <Phone className="w-4 h-4 text-brand-red-light shrink-0" />
                <span>+94 11 234 5678 / +94 77 123 4567</span>
              </li>
              <li className="flex items-center gap-2.5">
                <Mail className="w-4 h-4 text-brand-red-light shrink-0" />
                <span>info@milanosalon.lk</span>
              </li>
              <li className="flex items-center gap-2.5">
                <Clock className="w-4 h-4 text-brand-red-light shrink-0" />
                <span>Mon - Sun: 9:00 AM - 8:00 PM</span>
              </li>
            </ul>
          </div>
        </div>

        <div className="pt-6 flex flex-col sm:flex-row items-center justify-between gap-4 text-xs text-brand-silver">
          <p>© {new Date().getFullYear()} Milano Salon. All rights reserved.</p>

          <div className="flex items-center gap-6">
            <a href="#" className="hover:text-white transition-colors">
              Privacy Policy
            </a>
            <a href="#" className="hover:text-white transition-colors">
              Terms of Service
            </a>
          </div>
        </div>
      </div>
    </footer>
  );
};

export default FooterSection;
