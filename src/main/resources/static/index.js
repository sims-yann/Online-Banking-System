document.addEventListener('DOMContentLoaded', function() {
    // Mobile Menu Toggle
    const mobileMenuToggle = document.querySelector('.mobile-menu-toggle');
    const navbar = document.querySelector('.navbar');

    if (mobileMenuToggle) {
        mobileMenuToggle.addEventListener('click', function() {
            // Create a mobile menu only when it's clicked for the first time
            if (!document.querySelector('.mobile-menu')) {
                const mobileMenu = document.createElement('div');
                mobileMenu.className = 'mobile-menu';

                // Clone navigation links
                const navLinks = document.querySelector('.navbar-links').cloneNode(true);
                mobileMenu.appendChild(navLinks);

                // Add login/register buttons
                const navButtons = document.createElement('div');
                navButtons.className = 'mobile-nav-buttons';
                navButtons.innerHTML = `
          <a href="/login" class="login-button">Login</a>
          <a href="/register" class="register-button">Register</a>
        `;
                mobileMenu.appendChild(navButtons);

                // Append to navbar
                navbar.appendChild(mobileMenu);

                // Style the mobile menu
                applyMobileMenuStyles(mobileMenu, navButtons);
            } else {
                // Toggle the mobile menu visibility
                const mobileMenu = document.querySelector('.mobile-menu');
                mobileMenu.classList.toggle('active');
            }
        });
    }

    function applyMobileMenuStyles(mobileMenu, navButtons) {
        // Mobile menu styles
        mobileMenu.style.position = 'absolute';
        mobileMenu.style.top = '100%';
        mobileMenu.style.left = '0';
        mobileMenu.style.width = '100%';
        mobileMenu.style.backgroundColor = 'white';
        mobileMenu.style.boxShadow = '0 10px 15px rgba(0, 0, 0, 0.1)';
        mobileMenu.style.padding = '1.5rem';
        mobileMenu.style.display = 'none';
        mobileMenu.style.zIndex = '1000';
        mobileMenu.classList.add('active');

        // Override the display property to show the menu
        mobileMenu.classList.add('active');
        mobileMenu.style.display = 'block';

        // Style menu links
        const links = mobileMenu.querySelectorAll('ul li');
        links.forEach(link => {
            link.style.display = 'block';
            link.style.margin = '1rem 0';
            link.style.textAlign = 'center';
        });

        // Style buttons container
        navButtons.style.display = 'flex';
        navButtons.style.flexDirection = 'column';
        navButtons.style.gap = '1rem';
        navButtons.style.marginTop = '1.5rem';

        // Style individual buttons
        const buttons = navButtons.querySelectorAll('a');
        buttons.forEach(button => {
            button.style.display = 'block';
            button.style.textAlign = 'center';
            button.style.padding = '0.75rem';
        });
    }

    // Testimonial Slider
    let currentSlide = 0;
    const testimonials = document.querySelectorAll('.testimonial-card');
    const dots = document.querySelectorAll('.dot');

    if (testimonials.length > 0 && dots.length > 0) {
        // Set initial state
        updateSlider();

        // Handle dot clicks
        dots.forEach((dot, index) => {
            dot.addEventListener('click', () => {
                currentSlide = index;
                updateSlider();
            });
        });

        // Auto rotate every 5 seconds
        setInterval(() => {
            currentSlide = (currentSlide + 1) % testimonials.length;
            updateSlider();
        }, 5000);
    }

    function updateSlider() {
        // Update testimonials
        testimonials.forEach((slide, index) => {
            if (index === currentSlide) {
                slide.style.display = 'block';
            } else {
                slide.style.display = 'none';
            }
        });

        // Update dots
        dots.forEach((dot, index) => {
            if (index === currentSlide) {
                dot.classList.add('active');
            } else {
                dot.classList.remove('active');
            }
        });
    }

    // Smooth scroll for anchor links
    document.querySelectorAll('a[href^="#"]').forEach(anchor => {
        anchor.addEventListener('click', function(e) {
            e.preventDefault();

            const targetId = this.getAttribute('href');
            if (targetId === '#') return;

            const targetElement = document.querySelector(targetId);
            if (targetElement) {
                window.scrollTo({
                    top: targetElement.offsetTop - 80, // Account for navbar height
                    behavior: 'smooth'
                });
            }
        });
    });

    // Check for stored user and redirect if logged in
    const currentUser = localStorage.getItem('currentUser');
    if (currentUser) {
        const user = JSON.parse(currentUser);
        if (user.role === 'admin') {
            // Don't auto-redirect to admin from landing page
            // window.location.href = '/admin';
        } else if (user.role === 'customer') {
            // Don't auto-redirect to dashboard from landing page
            // window.location.href = '/dashboard';
        }
    }
});
